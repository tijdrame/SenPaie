/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { StructureDetailComponent } from '../../../../../../main/webapp/app/entities/structure/structure-detail.component';
import { StructureService } from '../../../../../../main/webapp/app/entities/structure/structure.service';
import { Structure } from '../../../../../../main/webapp/app/entities/structure/structure.model';

describe('Component Tests', () => {

    describe('Structure Management Detail Component', () => {
        let comp: StructureDetailComponent;
        let fixture: ComponentFixture<StructureDetailComponent>;
        let service: StructureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [StructureDetailComponent],
                providers: [
                    StructureService
                ]
            })
            .overrideTemplate(StructureDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StructureDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StructureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Structure(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.structure).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
