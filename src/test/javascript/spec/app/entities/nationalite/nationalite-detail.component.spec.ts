/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { NationaliteDetailComponent } from '../../../../../../main/webapp/app/entities/nationalite/nationalite-detail.component';
import { NationaliteService } from '../../../../../../main/webapp/app/entities/nationalite/nationalite.service';
import { Nationalite } from '../../../../../../main/webapp/app/entities/nationalite/nationalite.model';

describe('Component Tests', () => {

    describe('Nationalite Management Detail Component', () => {
        let comp: NationaliteDetailComponent;
        let fixture: ComponentFixture<NationaliteDetailComponent>;
        let service: NationaliteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [NationaliteDetailComponent],
                providers: [
                    NationaliteService
                ]
            })
            .overrideTemplate(NationaliteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NationaliteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NationaliteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Nationalite(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.nationalite).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
