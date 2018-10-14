import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { AbsenceComponent } from './absence.component';
import { AbsenceDetailComponent } from './absence-detail.component';
import { AbsencePopupComponent } from './absence-dialog.component';
import { AbsenceDeletePopupComponent } from './absence-delete-dialog.component';

@Injectable()
export class AbsenceResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const absenceRoute: Routes = [
    {
        path: 'absence',
        component: AbsenceComponent,
        resolve: {
            'pagingParams': AbsenceResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.absence.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'absence/:id',
        component: AbsenceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.absence.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const absencePopupRoute: Routes = [
    {
        path: 'absence-new',
        component: AbsencePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.absence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'absence/:id/edit',
        component: AbsencePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.absence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'absence/:id/delete',
        component: AbsenceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.absence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
