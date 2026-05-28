import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

import { LogApiService } from '../../../../core/services/api/log-api.service';

@Component({
  selector: 'app-upload-logs',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './upload-logs.component.html',
  styleUrls: ['./upload-logs.component.scss']
})
export class UploadLogsComponent {

  selectedFile: File | null = null;

  isUploading = false;

  uploadMessage = '';

  constructor(
    private logApiService: LogApiService
  ) {}

  onFileSelected(event: any): void {

    const file = event.target.files[0];

    if (file) {

      this.selectedFile = file;

      this.uploadMessage = '';
    }

  }

  uploadLogs(): void {

    if (!this.selectedFile) {

      this.uploadMessage = 'Please select a log file first';

      return;
    }

    this.isUploading = true;

    this.logApiService
      .uploadLogs(this.selectedFile)
      .subscribe({
        next: (response) => {

          console.log(response);

          this.isUploading = false;

          this.uploadMessage = response.message;
        },
        error: (error) => {

          console.error(error);

          this.isUploading = false;

          this.uploadMessage = 'Upload failed';
        }
      });

  }

}