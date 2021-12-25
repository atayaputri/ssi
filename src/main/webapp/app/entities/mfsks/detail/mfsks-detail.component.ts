import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMFSKS } from '../mfsks.model';

@Component({
  selector: 'jhi-mfsks-detail',
  templateUrl: './mfsks-detail.component.html',
})
export class MFSKSDetailComponent implements OnInit {
  mFSKS: IMFSKS | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mFSKS }) => {
      this.mFSKS = mFSKS;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
