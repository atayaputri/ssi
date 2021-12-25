import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMFSHM } from '../mfshm.model';

@Component({
  selector: 'jhi-mfshm-detail',
  templateUrl: './mfshm-detail.component.html',
})
export class MFSHMDetailComponent implements OnInit {
  mFSHM: IMFSHM | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mFSHM }) => {
      this.mFSHM = mFSHM;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
