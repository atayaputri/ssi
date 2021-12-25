import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMFSHM } from '../mfshm.model';
import { MFSHMService } from '../service/mfshm.service';
import { MFSHMDeleteDialogComponent } from '../delete/mfshm-delete-dialog.component';

@Component({
  selector: 'jhi-mfshm',
  templateUrl: './mfshm.component.html',
})
export class MFSHMComponent implements OnInit {
  mFSHMS?: IMFSHM[];
  isLoading = false;

  constructor(protected mFSHMService: MFSHMService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.mFSHMService.query().subscribe(
      (res: HttpResponse<IMFSHM[]>) => {
        this.isLoading = false;
        this.mFSHMS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: IMFSHM): number {
    return item.id!;
  }

  delete(mFSHM: IMFSHM): void {
    const modalRef = this.modalService.open(MFSHMDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mFSHM = mFSHM;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
