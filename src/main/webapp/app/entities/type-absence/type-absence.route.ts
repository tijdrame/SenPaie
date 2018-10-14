import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TypeAbsenceComponent } from './type-absence.component';
import { TypeAbsenceDetailComponent } from './type-absence-detail.component';
import { TypeAbsencePopupComponent } from './type-absence-dialog.component';
import { TypeAbsenceDeletePopupComponent } from './type-absence-delete-dialog.component';

@Injectable()
export class TypeAbsenceResolvePagingParams implements Resolve<any> {

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

export const typeAbsenceRoute: Routes = [
    {
        path: 'type-absence',
        component: TypeAbsenceComponent,
        resolve: {
            'pagingParams': TypeAbsenceResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typeAbsence.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-absence/:id',
        component: TypeAbsenceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typeAbsence.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeAbsencePopupRoute: Routes = [
    {
        path: 'type-absence-new',
        component: TypeAbsencePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typeAbsence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-absence/:id/edit',
        component: TypeAbsencePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typeAbsence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-absence/:id/delete',
        component: TypeAbsenceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typeAbsence.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
