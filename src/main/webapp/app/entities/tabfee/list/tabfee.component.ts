import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITABFEE } from '../tabfee.model';
import { TABFEEService } from '../service/tabfee.service';
import { TABFEEDeleteDialogComponent } from '../delete/tabfee-delete-dialog.component';

@Component({
  selector: 'jhi-tabfee',
  templateUrl: './tabfee.component.html',
})
export class TABFEEComponent implements OnInit {
  tABFEES?: ITABFEE[];
  isLoading = false;

  constructor(protected tABFEEService: TABFEEService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tABFEEService.query().subscribe(
      (res: HttpResponse<ITABFEE[]>) => {
        this.isLoading = false;
        this.tABFEES = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITABFEE): number {
    return item.id!;
  }

  delete(tABFEE: ITABFEE): void {
    const modalRef = this.modalService.open(TABFEEDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tABFEE = tABFEE;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
