import {Component, inject, NgZone, OnInit, signal, WritableSignal} from '@angular/core';
import {MatIcon, MatIconRegistry} from "@angular/material/icon";
import {ParsingService} from '../../service/parsing.service';
import {AoCSolution} from '../../module/aoc-solution.module';
import {HttpClient, HttpErrorResponse, HttpStatusCode} from '@angular/common/http';
import {MatCard, MatCardContent, MatCardHeader, MatCardTitle} from '@angular/material/card';
import {MatMiniFabButton} from '@angular/material/button';
import {
  MatAccordion,
  MatExpansionPanel,
  MatExpansionPanelHeader,
  MatExpansionPanelTitle
} from '@angular/material/expansion';
import {MatError, MatFormField, MatInput, MatLabel} from '@angular/material/input';
import {MatOption, MatSelect} from '@angular/material/select';
import {FormControl, FormsModule, ReactiveFormsModule, Validators} from '@angular/forms';
import {Years} from '../../module/year.module';
import {MatCheckbox} from '@angular/material/checkbox';
import {SolutionCheckPipe} from '../../pipe/solution-check.pipe';
import {NgClass, NgIf, NgOptimizedImage} from '@angular/common';
import {environment} from '../../../environment/environment';
import {MatTab, MatTabGroup, MatTabLabel} from '@angular/material/tabs';
import {DomSanitizer} from '@angular/platform-browser';
import {Language} from '../../module/language.module';
import {ImplementationResultModule} from '../../module/implementation-result-module';

export interface Filter {
  solution: boolean;
  showStatus: boolean;
  showDifference: boolean;
  implementation: boolean;
}

export interface PartStatus<T> {
  part1: T;
  part2: T;
}

export enum Part {
  PART_ONE = 1,
  PART_TWO = 2,
}

@Component({
  selector: 'app-parser',
  standalone: true,
  imports: [
    SolutionCheckPipe,
    MatIcon,
    MatCard,
    MatCardHeader,
    MatCardTitle,
    MatCardContent,
    MatMiniFabButton,
    MatExpansionPanel,
    MatExpansionPanelHeader,
    MatExpansionPanelTitle,
    MatAccordion,
    MatFormField,
    MatLabel,
    MatSelect,
    MatOption,
    ReactiveFormsModule,
    MatError,
    MatInput,
    MatCheckbox,
    FormsModule,
    NgClass,
    MatTabGroup,
    MatTab,
    MatTabLabel,
    NgOptimizedImage,
    NgIf
  ],
  templateUrl: './parser.html',
  styleUrl: './parser.scss',
})
export class Parser implements OnInit {

  constructor(iconRegistry: MatIconRegistry, sanitizer: DomSanitizer) {
    iconRegistry.addSvgIcon(
      'go-icon',
      sanitizer.bypassSecurityTrustResourceUrl('icons/Go-Logo_Aqua.svg')
    );
    iconRegistry.addSvgIcon(
      'kotlin-icon',
      sanitizer.bypassSecurityTrustResourceUrl('icons/Kotlin_Logo.svg')
    );
  }


  private readonly parsingService = inject(ParsingService);
  private readonly ngZone = inject(NgZone);
  private readonly http = inject(HttpClient);
  protected readonly environment = environment;

  aocSolution: WritableSignal<AoCSolution | undefined> = signal(undefined);
  kotlinImplementation: WritableSignal<ImplementationResultModule| undefined> = signal(undefined);
  goImplementation: WritableSignal<ImplementationResultModule | undefined> = signal(undefined);

  yearsControl: FormControl<number | null> = new FormControl(null, Validators.required);
  daysControl: FormControl<number | null> = new FormControl(null, Validators.required);

  years: Map<number, number[]> = new Map();
  availableDays: number[] = [];

  fileName: string | undefined;
  filter: Filter = {
    showStatus: false,
    showDifference: false,
    solution: false,
    implementation: false,
  };

  answerPart1: number | undefined = undefined;
  answerPart2: number | undefined = undefined;

  status: PartStatus<string> = {
    part1: '',
    part2: '',
  }

  difference: PartStatus<string> = {
    part1: '',
    part2: '',
  }

  solutionCheck: PartStatus<boolean | undefined> = {
    part1: undefined,
    part2: undefined,
  }

  ngOnInit(): void {
    this.getCatalogue();
    this.getDaysForSelectedYear();
  }

  public daySelectErrorMessage(): string {
    return this.availableDays.length === 0 ? "Please select a year first" : "Please select a day"
  }

  public onFileSelected(event: any): void {
    if (this.yearsControl.value === null || this.daysControl.value === null) {
      alert("Please select a year and day first");
      return;
    }

    const file: File = event.target.files[0];

    this.fileName = file.name;

    if (file) {
      const formData: FormData = new FormData();

      formData.append('file', file);

      this.parsingService
      .uploadFile$(this.yearsControl.value, this.daysControl.value, formData)
      .subscribe({
        next: (solution: AoCSolution) => {
          this.ngZone.run(() => {
            this.aocSolution.set(solution);
          })
        },
        error: (error: HttpErrorResponse) => {
          if (error.status === HttpStatusCode.BadRequest) {
            alert(`Wrong input for year ${this.yearsControl.value} day ${this.daysControl.value}`);
          } else {
            alert("Unknown error. Please reload the page.");
          }
        },
      });
    }
  }

  setFilterResults(part: Part) {
    this.setStatus(part);
    this.setDifference(part);
    this.setSolutionCheck(part);
  }

  private getCatalogue(): void {
    this.parsingService.availability$().subscribe({
      next: (years: Years) => {
        years.years.forEach((year) => {
          this.years.set(year.year, year.days)
        })
      },
      error: (_: HttpErrorResponse) => {
        alert("Couldn't load available years and days. Please try it again later.");
      },
    })
  }

  private getDaysForSelectedYear(): void {
    this.yearsControl.valueChanges.subscribe(year => {
      if (year == null) {
        this.availableDays = [];
        this.daysControl.reset();
        return;
      }

      this.availableDays = this.years.get(year) ?? [];
      this.daysControl.reset();
    });
  }

  private setSolutionCheck(part: Part): void {
    switch (part) {
      case Part.PART_ONE:
        this.solutionCheck.part1 = this.checkSolution(this.answerPart1, this.aocSolution()?.part1);
        break;
      case Part.PART_TWO:
        this.solutionCheck.part2 = this.checkSolution(this.answerPart2, this.aocSolution()?.part2);
        break;
    }
  }

  private checkSolution(answer: number | undefined, solution: number | undefined): boolean | undefined {
    if (answer !== undefined && solution !== undefined) {
      return answer === solution
    }
    return undefined;
  }

  private setStatus(part: Part): void {
    switch (part) {
      case Part.PART_ONE:
        this.status.part1 = this.calculateStatus(this.answerPart1, this.aocSolution()?.part1);
        break;
      case Part.PART_TWO:
        this.status.part2 = this.calculateStatus(this.answerPart2, this.aocSolution()?.part2);
        break;
    }
  }

  private calculateStatus(answer: number | undefined, solution: number | undefined): string {
    if (answer === undefined && solution === undefined) {
      return '';
    } else {
      if (answer! < solution!) {
        return "Your answer is to low";
      }
      if (answer! > solution!) {
        return "Your answer is to high";
      }

      return "Your answer is correct";
    }
  }

  private setDifference(part: Part): void {
    switch (part) {
      case Part.PART_ONE:
        this.difference.part1 = this.calculateDifference(this.answerPart1, this.aocSolution()?.part1);
        break;
      case Part.PART_TWO:
        this.difference.part2 = this.calculateDifference(this.answerPart2, this.aocSolution()?.part2);
        break;
    }
  }

  private calculateDifference(answer: number | undefined, solution: number | undefined): string {
    if (answer === undefined && solution === undefined) {
      return '';
    } else {
      return Math.abs(answer! - solution!).toString()
    }
  }

  loadImplementation(): void {
    if (this.yearsControl.value === null || this.daysControl.value === null) {
      return;
    }
    // Load kotlin implementation
    this.parsingService
    .getImplementation$(Language.KOTLIN, this.yearsControl.value, this.daysControl.value)
    .subscribe({
      next: (implementation: ImplementationResultModule) => {
        this.ngZone.run(() => {
          this.kotlinImplementation.set(implementation);
        })
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === HttpStatusCode.BadRequest) {
          alert(`Wrong input for year ${this.yearsControl.value} day ${this.daysControl.value}`);
        } else {
          console.error(error);
          alert("Unknown error. Please reload the page.");
        }
      },
    });

    // Load go implementation
    this.parsingService
    .getImplementation$(Language.GO, this.yearsControl.value, this.daysControl.value)
    .subscribe({
      next: (implementation: ImplementationResultModule) => {
        this.ngZone.run(() => {
          this.goImplementation.set(implementation);
        })
      },
      error: (error: HttpErrorResponse) => {
        if (error.status === HttpStatusCode.BadRequest) {
          alert(`Wrong input for year ${this.yearsControl.value} day ${this.daysControl.value}`);
        } else {
          console.error(error);
          alert("Unknown error. Please reload the page.");
        }
      },
    });
  }
}
