import {Component, inject} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from '@angular/material/dialog';
import {MatButton} from '@angular/material/button';

export interface ErrorMessage {
  message: string;
}

@Component({
  selector: 'aoc-error-modal',
  standalone: true,
  imports: [
    MatDialogContent,
    MatDialogActions,
    MatButton,
    MatDialogTitle
  ],
  templateUrl: './error-modal.html',
  styleUrl: './error-modal.scss',
})
export class ErrorModal {
  readonly dialogRef = inject(MatDialogRef<ErrorModal>);
  readonly data = inject<ErrorMessage>(MAT_DIALOG_DATA);

  onOkayClick(): void {
    this.dialogRef.close();
  }
}
