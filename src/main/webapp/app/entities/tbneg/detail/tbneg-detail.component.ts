import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITBNEG } from '../tbneg.model';

@Component({
  selector: 'jhi-tbneg-detail',
  templateUrl: './tbneg-detail.component.html',
})
export class TBNEGDetailComponent implements OnInit {
  tBNEG: ITBNEG | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBNEG }) => {
      this.tBNEG = tBNEG;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
