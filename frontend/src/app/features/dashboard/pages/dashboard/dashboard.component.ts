import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

import { TestApiService } from '../../../../core/services/api/test-api.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  constructor(
    private testApiService: TestApiService
  ) {}

  ngOnInit(): void {

    this.testApiService.testRequest()
      .subscribe({
        next: (response) => {
          console.log(response);
        },
        error: (error) => {
          console.error(error);
        }
      });

  }

}