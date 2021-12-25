import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITBJNPS } from '../tbjnps.model';

@Component({
  selector: 'jhi-tbjnps-detail',
  templateUrl: './tbjnps-detail.component.html',
})
export class TBJNPSDetailComponent implements OnInit {
  tBJNPS: ITBJNPS | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBJNPS }) => {
      this.tBJNPS = tBJNPS;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
