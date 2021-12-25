import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITBKAB } from '../tbkab.model';

@Component({
  selector: 'jhi-tbkab-detail',
  templateUrl: './tbkab-detail.component.html',
})
export class TBKABDetailComponent implements OnInit {
  tBKAB: ITBKAB | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBKAB }) => {
      this.tBKAB = tBKAB;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
