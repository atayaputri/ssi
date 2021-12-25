import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMFSKS } from '../mfsks.model';
import { MFSKSService } from '../service/mfsks.service';

@Component({
  templateUrl: './mfsks-delete-dialog.component.html',
})
export class MFSKSDeleteDialogComponent {
  mFSKS?: IMFSKS;

  constructor(protected mFSKSService: MFSKSService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mFSKSService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
