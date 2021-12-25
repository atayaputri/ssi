import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMFSHM } from '../mfshm.model';
import { MFSHMService } from '../service/mfshm.service';

@Component({
  templateUrl: './mfshm-delete-dialog.component.html',
})
export class MFSHMDeleteDialogComponent {
  mFSHM?: IMFSHM;

  constructor(protected mFSHMService: MFSHMService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mFSHMService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
