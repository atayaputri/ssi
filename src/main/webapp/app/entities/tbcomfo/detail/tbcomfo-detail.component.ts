import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITBCOMFO } from '../tbcomfo.model';

@Component({
  selector: 'jhi-tbcomfo-detail',
  templateUrl: './tbcomfo-detail.component.html',
})
export class TBCOMFODetailComponent implements OnInit {
  tBCOMFO: ITBCOMFO | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBCOMFO }) => {
      this.tBCOMFO = tBCOMFO;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
