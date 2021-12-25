import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IMFHDR } from '../mfhdr.model';
import { MFHDRService } from '../service/mfhdr.service';
import { MFHDRDeleteDialogComponent } from '../delete/mfhdr-delete-dialog.component';

@Component({
  selector: 'jhi-mfhdr',
  templateUrl: './mfhdr.component.html',
})
export class MFHDRComponent implements OnInit {
  mFHDRS?: IMFHDR[];
  isLoading = false;

  constructor(protected mFHDRService: MFHDRService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.mFHDRService.query().subscribe(
      (res: HttpResponse<IMFHDR[]>) => {
        this.isLoading = false;
        this.mFHDRS = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackHdno(index: number, item: IMFHDR): number {
    return item.hdno!;
  }

  delete(mFHDR: IMFHDR): void {
    const modalRef = this.modalService.open(MFHDRDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.mFHDR = mFHDR;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
