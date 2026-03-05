import { Component, OnInit } from '@angular/core';
import {
  ListeningHistoryItem,
  ListeningHistoryService
} from '../../core/service/listening-history.service';

@Component({
  selector: 'app-listening-history',
  templateUrl: './listening-history.component.html',
  styleUrls: ['./listening-history.component.css']
})
export class ListeningHistoryComponent implements OnInit {
  historyItems: ListeningHistoryItem[] = [];
  loading = false;
  errorMessage = '';

  constructor(private listeningHistoryService: ListeningHistoryService) {}

  ngOnInit(): void {
    this.loadHistory();
  }

  clearHistory(): void {
    this.loading = true;
    this.errorMessage = '';
    this.listeningHistoryService.clearHistory().subscribe({
      next: () => {
        this.loadHistory();
      },
      error: () => {
        this.loading = false;
        this.errorMessage = 'Unable to clear history right now.';
      }
    });
  }

  trackByHistoryId(index: number, item: ListeningHistoryItem): string {
    return `${item.historyId}-${index}`;
  }

  private loadHistory(): void {
    this.loading = true;
    this.errorMessage = '';
    this.listeningHistoryService.getRecentHistory(50).subscribe({
      next: (items) => {
        this.historyItems = items;
        this.loading = false;
      },
      error: () => {
        this.historyItems = [];
        this.loading = false;
        this.errorMessage = 'Please log in to view listening history.';
      }
    });
  }
}
