import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBJNSHM } from '../tbjnshm.model';
import { TBJNSHMService } from '../service/tbjnshm.service';

@Component({
  templateUrl: './tbjnshm-delete-dialog.component.html',
})
export class TBJNSHMDeleteDialogComponent {
  tBJNSHM?: ITBJNSHM;

  constructor(protected tBJNSHMService: TBJNSHMService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.tBJNSHMService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
