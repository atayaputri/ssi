import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBPROV } from '../tbprov.model';
import { TBPROVService } from '../service/tbprov.service';
import { TBPROVDeleteDialogComponent } from '../delete/tbprov-delete-dialog.component';

@Component({
  selector: 'jhi-tbprov',
  templateUrl: './tbprov.component.html',
})
export class TBPROVComponent implements OnInit {
  tBPROVS?: ITBPROV[];
  isLoading = false;

  constructor(protected tBPROVService: TBPROVService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tBPROVService.query().subscribe(
      (res: HttpResponse<ITBPROV[]>) => {
        this.isLoading = false;
        this.tBPROVS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackProvcod(index: number, item: ITBPROV): string {
    return item.provcod!;
  }

  delete(tBPROV: ITBPROV): void {
    const modalRef = this.modalService.open(TBPROVDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tBPROV = tBPROV;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
