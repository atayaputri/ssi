import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITBPART } from '../tbpart.model';

@Component({
  selector: 'jhi-tbpart-detail',
  templateUrl: './tbpart-detail.component.html',
})
export class TBPARTDetailComponent implements OnInit {
  tBPART: ITBPART | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBPART }) => {
      this.tBPART = tBPART;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
