/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenPaieTestModule } from '../../../test.module';
import { AbsenceDialogComponent } from '../../../../../../main/webapp/app/entities/absence/absence-dialog.component';
import { AbsenceService } from '../../../../../../main/webapp/app/entities/absence/absence.service';
import { Absence } from '../../../../../../main/webapp/app/entities/absence/absence.model';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { CollaborateurService } from '../../../../../../main/webapp/app/entities/collaborateur';
import { MotifService } from '../../../../../../main/webapp/app/entities/motif';
import { ExerciceService } from '../../../../../../main/webapp/app/entities/exercice';

describe('Component Tests', () => {

    describe('Absence Management Dialog Component', () => {
        let comp: AbsenceDialogComponent;
        let fixture: ComponentFixture<AbsenceDialogComponent>;
        let service: AbsenceService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [AbsenceDialogComponent],
                providers: [
                    UserService,
                    CollaborateurService,
                    MotifService,
                    ExerciceService,
                    AbsenceService
                ]
            })
            .overrideTemplate(AbsenceDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AbsenceDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AbsenceService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Absence(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.absence = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'absenceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Absence();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.absence = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'absenceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
