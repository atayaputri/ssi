import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMAPSKS } from '../mapsks.model';

@Component({
  selector: 'jhi-mapsks-detail',
  templateUrl: './mapsks-detail.component.html',
})
export class MAPSKSDetailComponent implements OnInit {
  mAPSKS: IMAPSKS | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mAPSKS }) => {
      this.mAPSKS = mAPSKS;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
