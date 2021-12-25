import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { MFSHMService } from '../service/mfshm.service';

import { MFSHMComponent } from './mfshm.component';

describe('MFSHM Management Component', () => {
  let comp: MFSHMComponent;
  let fixture: ComponentFixture<MFSHMComponent>;
  let service: MFSHMService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [MFSHMComponent],
    })
      .overrideTemplate(MFSHMComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(MFSHMComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(MFSHMService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ id: 123 }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.mFSHMS?.[0]).toEqual(expect.objectContaining({ id: 123 }));
  });
});
