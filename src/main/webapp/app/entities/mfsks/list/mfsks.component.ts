import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMFSKS } from '../mfsks.model';
import { MFSKSService } from '../service/mfsks.service';
import { MFSKSDeleteDialogComponent } from '../delete/mfsks-delete-dialog.component';

@Component({
  selector: 'jhi-mfsks',
  templateUrl: './mfsks.component.html',
})
export class MFSKSComponent implements OnInit {
  mFSKS?: IMFSKS[];
  isLoading = false;

  constructor(protected mFSKSService: MFSKSService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.mFSKSService.query().subscribe(
      (res: HttpResponse<IMFSKS[]>) => {
        this.isLoading = false;
        this.mFSKS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackSkno(index: number, item: IMFSKS): number {
    return item.skno!;
  }

  delete(mFSKS: IMFSKS): void {
    const modalRef = this.modalService.open(MFSKSDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mFSKS = mFSKS;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
