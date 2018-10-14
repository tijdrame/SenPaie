/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { SexeDetailComponent } from '../../../../../../main/webapp/app/entities/sexe/sexe-detail.component';
import { SexeService } from '../../../../../../main/webapp/app/entities/sexe/sexe.service';
import { Sexe } from '../../../../../../main/webapp/app/entities/sexe/sexe.model';

describe('Component Tests', () => {

    describe('Sexe Management Detail Component', () => {
        let comp: SexeDetailComponent;
        let fixture: ComponentFixture<SexeDetailComponent>;
        let service: SexeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [SexeDetailComponent],
                providers: [
                    SexeService
                ]
            })
            .overrideTemplate(SexeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SexeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SexeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Sexe(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.sexe).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
