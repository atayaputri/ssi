jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TBCOMSService } from '../service/tbcoms.service';

import { TBCOMSDeleteDialogComponent } from './tbcoms-delete-dialog.component';

describe('TBCOMS Management Delete Component', () => {
  let comp: TBCOMSDeleteDialogComponent;
  let fixture: ComponentFixture<TBCOMSDeleteDialogComponent>;
  let service: TBCOMSService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBCOMSDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(TBCOMSDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBCOMSDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBCOMSService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

        // WHEN
        comp.confirmDelete('ABC');
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith('ABC');
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
