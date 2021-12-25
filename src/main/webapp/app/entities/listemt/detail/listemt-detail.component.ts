import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILISTEMT } from '../listemt.model';

@Component({
  selector: 'jhi-listemt-detail',
  templateUrl: './listemt-detail.component.html',
})
export class LISTEMTDetailComponent implements OnInit {
  lISTEMT: ILISTEMT | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ lISTEMT }) => {
      this.lISTEMT = lISTEMT;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
