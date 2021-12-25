import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBHDR } from '../tbhdr.model';
import { TBHDRService } from '../service/tbhdr.service';
import { TBHDRDeleteDialogComponent } from '../delete/tbhdr-delete-dialog.component';

@Component({
  selector: 'jhi-tbhdr',
  templateUrl: './tbhdr.component.html',
})
export class TBHDRComponent implements OnInit {
  tBHDRS?: ITBHDR[];
  isLoading = false;

  constructor(protected tBHDRService: TBHDRService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.tBHDRService.query().subscribe(
      (res: HttpResponse<ITBHDR[]>) => {
        this.isLoading = false;
        this.tBHDRS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackThno(index: number, item: ITBHDR): number {
    return item.thno!;
  }

  delete(tBHDR: ITBHDR): void {
    const modalRef = this.modalService.open(TBHDRDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tBHDR = tBHDR;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
