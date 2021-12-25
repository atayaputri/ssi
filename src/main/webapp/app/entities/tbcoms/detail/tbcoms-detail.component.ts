import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITBCOMS } from '../tbcoms.model';

@Component({
  selector: 'jhi-tbcoms-detail',
  templateUrl: './tbcoms-detail.component.html',
})
export class TBCOMSDetailComponent implements OnInit {
  tBCOMS: ITBCOMS | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBCOMS }) => {
      this.tBCOMS = tBCOMS;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
