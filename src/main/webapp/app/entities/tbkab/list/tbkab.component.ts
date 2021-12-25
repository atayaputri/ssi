import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBKAB } from '../tbkab.model';
import { TBKABService } from '../service/tbkab.service';
import { TBKABDeleteDialogComponent } from '../delete/tbkab-delete-dialog.component';

@Component({
  selector: 'jhi-tbkab',
  templateUrl: './tbkab.component.html',
})
export class TBKABComponent implements OnInit {
  tBKABS?: ITBKAB[];
  isLoading = false;

  constructor(protected tBKABService: TBKABService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tBKABService.query().subscribe(
      (res: HttpResponse<ITBKAB[]>) => {
        this.isLoading = false;
        this.tBKABS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackKabcod(index: number, item: ITBKAB): string {
    return item.kabcod!;
  }

  delete(tBKAB: ITBKAB): void {
    const modalRef = this.modalService.open(TBKABDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tBKAB = tBKAB;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
