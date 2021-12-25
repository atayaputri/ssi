import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IMFHDR } from '../mfhdr.model';
import { MFHDRService } from '../service/mfhdr.service';

@Component({
  templateUrl: './mfhdr-delete-dialog.component.html',
})
export class MFHDRDeleteDialogComponent {
  mFHDR?: IMFHDR;

  constructor(protected mFHDRService: MFHDRService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.mFHDRService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
