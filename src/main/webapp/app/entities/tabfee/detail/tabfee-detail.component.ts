import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITABFEE } from '../tabfee.model';

@Component({
  selector: 'jhi-tabfee-detail',
  templateUrl: './tabfee-detail.component.html',
})
export class TABFEEDetailComponent implements OnInit {
  tABFEE: ITABFEE | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tABFEE }) => {
      this.tABFEE = tABFEE;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
