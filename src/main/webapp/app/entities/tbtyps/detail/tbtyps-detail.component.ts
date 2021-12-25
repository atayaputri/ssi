import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITBTYPS } from '../tbtyps.model';

@Component({
  selector: 'jhi-tbtyps-detail',
  templateUrl: './tbtyps-detail.component.html',
})
export class TBTYPSDetailComponent implements OnInit {
  tBTYPS: ITBTYPS | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBTYPS }) => {
      this.tBTYPS = tBTYPS;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
