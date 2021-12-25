import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBHDR } from '../tbhdr.model';
import { TBHDRService } from '../service/tbhdr.service';

@Component({
  templateUrl: './tbhdr-delete-dialog.component.html',
})
export class TBHDRDeleteDialogComponent {
  tBHDR?: ITBHDR;

  constructor(protected tBHDRService: TBHDRService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tBHDRService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
