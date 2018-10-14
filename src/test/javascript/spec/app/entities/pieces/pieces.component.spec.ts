/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { PiecesComponent } from '../../../../../../main/webapp/app/entities/pieces/pieces.component';
import { PiecesService } from '../../../../../../main/webapp/app/entities/pieces/pieces.service';
import { Pieces } from '../../../../../../main/webapp/app/entities/pieces/pieces.model';

describe('Component Tests', () => {

    describe('Pieces Management Component', () => {
        let comp: PiecesComponent;
        let fixture: ComponentFixture<PiecesComponent>;
        let service: PiecesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [PiecesComponent],
                providers: [
                    PiecesService
                ]
            })
            .overrideTemplate(PiecesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PiecesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PiecesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Pieces(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.pieces[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
