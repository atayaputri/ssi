import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITBHDR } from '../tbhdr.model';

@Component({
  selector: 'jhi-tbhdr-detail',
  templateUrl: './tbhdr-detail.component.html',
})
export class TBHDRDetailComponent implements OnInit {
  tBHDR: ITBHDR | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tBHDR }) => {
      this.tBHDR = tBHDR;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
