import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TBJNSHMDetailComponent } from './tbjnshm-detail.component';

describe('TBJNSHM Management Detail Component', () => {
  let comp: TBJNSHMDetailComponent;
  let fixture: ComponentFixture<TBJNSHMDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TBJNSHMDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tBJNSHM: { jshcod: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(TBJNSHMDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBJNSHMDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tBJNSHM on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tBJNSHM).toEqual(expect.objectContaining({ jshcod: 'ABC' }));
    });
  });
});
