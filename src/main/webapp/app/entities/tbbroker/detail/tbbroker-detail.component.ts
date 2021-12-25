import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITBBROKER } from '../tbbroker.model';

@Component({
  selector: 'jhi-tbbroker-detail',
  templateUrl: './tbbroker-detail.component.html',
})
export class TBBROKERDetailComponent implements OnInit {
  tBBROKER: ITBBROKER | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBBROKER }) => {
      this.tBBROKER = tBBROKER;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
