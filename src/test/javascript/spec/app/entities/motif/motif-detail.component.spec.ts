/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { MotifDetailComponent } from '../../../../../../main/webapp/app/entities/motif/motif-detail.component';
import { MotifService } from '../../../../../../main/webapp/app/entities/motif/motif.service';
import { Motif } from '../../../../../../main/webapp/app/entities/motif/motif.model';

describe('Component Tests', () => {

    describe('Motif Management Detail Component', () => {
        let comp: MotifDetailComponent;
        let fixture: ComponentFixture<MotifDetailComponent>;
        let service: MotifService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [MotifDetailComponent],
                providers: [
                    MotifService
                ]
            })
            .overrideTemplate(MotifDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MotifDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MotifService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Motif(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.motif).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
