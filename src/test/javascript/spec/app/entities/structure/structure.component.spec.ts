/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { StructureComponent } from '../../../../../../main/webapp/app/entities/structure/structure.component';
import { StructureService } from '../../../../../../main/webapp/app/entities/structure/structure.service';
import { Structure } from '../../../../../../main/webapp/app/entities/structure/structure.model';

describe('Component Tests', () => {

    describe('Structure Management Component', () => {
        let comp: StructureComponent;
        let fixture: ComponentFixture<StructureComponent>;
        let service: StructureService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [StructureComponent],
                providers: [
                    StructureService
                ]
            })
            .overrideTemplate(StructureComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StructureComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StructureService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Structure(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.structures[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
