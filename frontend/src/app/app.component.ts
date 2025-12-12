import {Component, inject} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterOutlet} from '@angular/router';
import {HttpErrorResponse} from "@angular/common/http";
import {UploadFileService} from "./service/upload-file.service";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title = 'frontend';
  file: any;

  uploadFileService = inject(UploadFileService);

  public onFileSelected(event: any): void {
    const file: File = event.target.files[0];

    if (file) {
      const formData: FormData = new FormData();

      formData.append('file', file);

      this.uploadFileService
      .uploadFile(formData)
      .subscribe({
        next: (file: any) => {
          this.file = file;
        },
        error: (error: HttpErrorResponse) => {
          alert(error);
        },
      });
    }
  }
}
