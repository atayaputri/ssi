jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TBBROKERService } from '../service/tbbroker.service';

import { TBBROKERDeleteDialogComponent } from './tbbroker-delete-dialog.component';

describe('TBBROKER Management Delete Component', () => {
  let comp: TBBROKERDeleteDialogComponent;
  let fixture: ComponentFixture<TBBROKERDeleteDialogComponent>;
  let service: TBBROKERService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TBBROKERDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(TBBROKERDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBBROKERDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TBBROKERService);
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
