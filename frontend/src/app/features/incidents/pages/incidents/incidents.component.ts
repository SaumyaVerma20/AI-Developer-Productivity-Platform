import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IncidentApiService } from '../../../../core/services/api/incident-api.service';

@Component({
  selector: 'app-incidents',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './incidents.component.html',
  styleUrls: ['./incidents.component.scss']
})
export class IncidentsComponent implements OnInit {

  incidents: any[] = [];

  filteredIncidents: any[] = [];

  searchTerm = '';

  constructor(
    private incidentApiService: IncidentApiService
  ) {}

  ngOnInit(): void {

    this.loadIncidents();

  }

  loadIncidents(): void {

    this.incidentApiService
      .getIncidents()
      .subscribe({
        next: (response) => {

          this.incidents = response;

          this.filteredIncidents = response;
        },
        error: (error) => {

          console.error(error);

        }
      });

  }

  filterIncidents(): void {

    const search = this.searchTerm.toLowerCase();

    this.filteredIncidents = this.incidents.filter(
      incident =>
        incident.id.toLowerCase().includes(search)
        ||
        incident.service.toLowerCase().includes(search)
        ||
        incident.severity.toLowerCase().includes(search)
        ||
        incident.status.toLowerCase().includes(search)
    );

  }

}