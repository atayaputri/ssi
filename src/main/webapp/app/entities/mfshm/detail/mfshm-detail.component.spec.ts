import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MFSHMDetailComponent } from './mfshm-detail.component';

describe('MFSHM Management Detail Component', () => {
  let comp: MFSHMDetailComponent;
  let fixture: ComponentFixture<MFSHMDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MFSHMDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ mFSHM: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MFSHMDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MFSHMDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mFSHM on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.mFSHM).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
