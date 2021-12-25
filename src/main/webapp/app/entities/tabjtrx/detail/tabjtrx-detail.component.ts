import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITABJTRX } from '../tabjtrx.model';

@Component({
  selector: 'jhi-tabjtrx-detail',
  templateUrl: './tabjtrx-detail.component.html',
})
export class TABJTRXDetailComponent implements OnInit {
  tABJTRX: ITABJTRX | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tABJTRX }) => {
      this.tABJTRX = tABJTRX;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
