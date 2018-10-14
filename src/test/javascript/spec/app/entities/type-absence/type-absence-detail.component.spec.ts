/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { TypeAbsenceDetailComponent } from '../../../../../../main/webapp/app/entities/type-absence/type-absence-detail.component';
import { TypeAbsenceService } from '../../../../../../main/webapp/app/entities/type-absence/type-absence.service';
import { TypeAbsence } from '../../../../../../main/webapp/app/entities/type-absence/type-absence.model';

describe('Component Tests', () => {

    describe('TypeAbsence Management Detail Component', () => {
        let comp: TypeAbsenceDetailComponent;
        let fixture: ComponentFixture<TypeAbsenceDetailComponent>;
        let service: TypeAbsenceService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [TypeAbsenceDetailComponent],
                providers: [
                    TypeAbsenceService
                ]
            })
            .overrideTemplate(TypeAbsenceDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TypeAbsenceDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeAbsenceService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TypeAbsence(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.typeAbsence).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
