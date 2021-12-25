import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITBJNSHM } from '../tbjnshm.model';

@Component({
  selector: 'jhi-tbjnshm-detail',
  templateUrl: './tbjnshm-detail.component.html',
})
export class TBJNSHMDetailComponent implements OnInit {
  tBJNSHM: ITBJNSHM | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBJNSHM }) => {
      this.tBJNSHM = tBJNSHM;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
