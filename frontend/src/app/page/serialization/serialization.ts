import {Component, inject, NgZone, OnInit, signal, WritableSignal} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {SerializationService} from '../../service/serialization.service';
import {AoCSolution} from '../../mdoule/aoc-solution.module';
import {HttpErrorResponse} from '@angular/common/http';
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
import {FormControl, ReactiveFormsModule, Validators} from '@angular/forms';
import {Years} from '../../mdoule/year.module';

@Component({
  selector: 'app-serialization',
  imports: [
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
    MatInput
  ],
  templateUrl: './serialization.html',
  styleUrl: './serialization.scss',
})
export class Serialization implements OnInit {
  private readonly serializationService = inject(SerializationService);
  private readonly ngZone = inject(NgZone);

  aocSolution: WritableSignal<AoCSolution | undefined> = signal(undefined);

  yearsControl: FormControl<number | null> = new FormControl(null, Validators.required);
  daysControl: FormControl<number | null> = new FormControl(null, Validators.required);

  years: Map<number, number[]> = new Map();
  availableDays: number[] = [];

  ngOnInit(): void {
    this.getCatalogue();
    this.getDaysForSelectedYear()
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

    if (file) {
      const formData: FormData = new FormData();

      formData.append('file', file);

      this.serializationService
      .uploadFile$(this.yearsControl.value, this.daysControl.value, formData)
      .subscribe({
        next: (solution: AoCSolution) => {
          this.ngZone.run(() => {
            this.aocSolution.set(solution);
          })
        },
        error: (error: HttpErrorResponse) => {
          alert(error);
        },
      });
    }
  }

  private getCatalogue(): void {
    this.serializationService.availability$().subscribe({
      next: (years: Years) => {
        years.years.forEach((year) => {
          this.years.set(year.year, year.days)
        })
      },
      error: (error: HttpErrorResponse) => {
        alert(error);
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
}
