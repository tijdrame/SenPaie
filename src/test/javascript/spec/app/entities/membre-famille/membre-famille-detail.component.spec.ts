/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { MembreFamilleDetailComponent } from '../../../../../../main/webapp/app/entities/membre-famille/membre-famille-detail.component';
import { MembreFamilleService } from '../../../../../../main/webapp/app/entities/membre-famille/membre-famille.service';
import { MembreFamille } from '../../../../../../main/webapp/app/entities/membre-famille/membre-famille.model';

describe('Component Tests', () => {

    describe('MembreFamille Management Detail Component', () => {
        let comp: MembreFamilleDetailComponent;
        let fixture: ComponentFixture<MembreFamilleDetailComponent>;
        let service: MembreFamilleService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [MembreFamilleDetailComponent],
                providers: [
                    MembreFamilleService
                ]
            })
            .overrideTemplate(MembreFamilleDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MembreFamilleDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MembreFamilleService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new MembreFamille(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.membreFamille).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
