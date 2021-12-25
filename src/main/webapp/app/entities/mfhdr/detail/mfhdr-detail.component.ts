import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMFHDR } from '../mfhdr.model';

@Component({
  selector: 'jhi-mfhdr-detail',
  templateUrl: './mfhdr-detail.component.html',
})
export class MFHDRDetailComponent implements OnInit {
  mFHDR: IMFHDR | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ mFHDR }) => {
      this.mFHDR = mFHDR;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
