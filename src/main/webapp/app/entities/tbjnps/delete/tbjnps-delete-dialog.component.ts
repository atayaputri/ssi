import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ITBJNPS } from '../tbjnps.model';
import { TBJNPSService } from '../service/tbjnps.service';

@Component({
  templateUrl: './tbjnps-delete-dialog.component.html',
})
export class TBJNPSDeleteDialogComponent {
  tBJNPS?: ITBJNPS;

  constructor(protected tBJNPSService: TBJNPSService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.tBJNPSService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
