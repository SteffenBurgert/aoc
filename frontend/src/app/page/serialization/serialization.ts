import {Component, inject, NgZone, signal, WritableSignal} from '@angular/core';
import {MatIcon} from "@angular/material/icon";
import {UploadFileService} from '../../service/upload-file.service';
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
export class Serialization {
  private readonly uploadFileService = inject(UploadFileService);
  private readonly ngZone = inject(NgZone);

  yearsControl = new FormControl<number | null>(null, Validators.required);
  daysControl = new FormControl<number | null>(null, Validators.required);

  years: Array<number> = [2025, 2024];
  days: Array<number> = [1, 2, 3, 4, 5, 6, 7];

  aocSolution: WritableSignal<AoCSolution | undefined> = signal<AoCSolution | undefined>(undefined);

  public onFileSelected(event: any): void {
    const file: File = event.target.files[0];

    if (file) {
      const formData: FormData = new FormData();

      formData.append('file', file);

      this.uploadFileService
      .uploadFile$(2025, 1, formData)
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
}
