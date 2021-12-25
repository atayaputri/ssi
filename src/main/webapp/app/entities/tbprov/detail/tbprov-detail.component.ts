import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITBPROV } from '../tbprov.model';

@Component({
  selector: 'jhi-tbprov-detail',
  templateUrl: './tbprov-detail.component.html',
})
export class TBPROVDetailComponent implements OnInit {
  tBPROV: ITBPROV | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBPROV }) => {
      this.tBPROV = tBPROV;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
